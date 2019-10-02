# partsbin

You see a tray full of interchangeable parts. You reach in and grab some and start putting things together. Something starts to form. You mostly like what you see but you need to swap out a few things. No problem, it's all super easy. You tinker a bit more and the next thing you know you have a working system.

This is partsbin - a set of reusable components and a philosophy for building simple systems.

The goal of this project is to:

1. Provide some best practices for designing simple, composable systems.
1. Provide a few useful functions for working with [Integrant](https://github.com/weavejester/integrant) or similar libraries. As such, when the namespace alias `ig` is used I am referring to `[integrant.core :as ig]`.
1. Provide reference implementations for some common parts.

As such, it can be used in a few ways:

1. Read it for good advice on how to build composable, data-driven systems (no need to use any of the code here, or just vendor what you will).
1. Use the top-level utility methods found in `partsbin.system`, `partsbin.middleware`, etc. but define your own `ig/init-key` methods.
1. Use the provided implementations of `ig/init-key` as well.

## Definitions

Since there are multiple frameworks out there for reloadable systems (e.g. [Integrant](https://github.com/weavejester/integrant), [Component](https://github.com/stuartsierra/component), [Mount](https://github.com/tolitius/mount), I need to clarify the following for this discussion:

* A **system** is the top level set of components to be used. For this project, it is the result of calling `integrant.core/init`.
* A **component** is a sub-element of a system which may have both init-time and runtime dependencies on other components in the system. I may also use 'part' interchangeably with component in this document.


To illustrate, here is a sample system with the various aspects labelled:
``` 
;The pre-initialized system configuration
(def config
  {::jdbc/connection    {:connection-uri "jdbc:h2:mem:mem_only"}
   ::datomic/database   {:db-uri  "datomic:mem://example"
                         :delete? true}
   ::datomic/connection {:database (ig/ref ::datomic/database)
                         :db-uri   "datomic:mem://example"}
   ::web/server         {:host         "0.0.0.0"
                         :port         3000
                         :sql-conn     (ig/ref ::jdbc/connection)
                         :datomic-conn (ig/ref ::datomic/connection)
                         :handler      #'app}})

;The initialized configuration is a system
(def system (ig/init config))

;Component or part refers to the same concept
(def web-component (system ::web/server))
(def web-part (system ::web/server))
```

## Philosophy & Best Practices

The overarching aim of partsbin is to provide a conceptual framework that facilitates functional, data-driven architectures. As such, here are some guiding principles and practices that will help to achieve this goal:

* Develop your API bottom-up with simple functions. Functions should be written with a _logical_ component in the first position and then a small number of arguments after. If more than 1 argument to the function is required beyond the component itself, consider passing a map in as the second argument instead.
* Do NOT write your APIs with partsbin or any other API, framework, or system that will consume it in mind. Write your functions as if you had no knowledge of the system(s) that will use it. This keeps your interfaces simple. This includes not using system keys in your functions.
  * Good: `(myfunction datomic-conn arg)` - Just takes a connection and arguments with no regard to partsbin or the system map.
  * Good: `(myfunction {:keys[datomic-conn]} arg)` - If it makes sense for the component to be a map, ensure that the keys are generic to what is being done in the calling function. `datomic-conn` implies that this is a generic Datomic connection and is not system or config specific.
  * Bad: `(myfunction {:keys[project-x/conn]} arg)` - relies on knowing the configuration/system keys of your system.
* When consuming other APIs from partsbin, consider initializing your components to be in the correct format to be used for those APIs. For example, [Clojure JDBC](http://clojure.github.io/java.jdbc/#clojure.java.jdbc) functions take a map in their first position and many [Monger](http://reference.clojuremongodb.info/) functions take a connection. By initializing your components in this fashion you can use those APIs directly without having to use wrappers or restructure your arguments.
* Try to use one part/component at a time in functions. If you need more than one part for a particular functionality, consider using an api such as core.async or rx so that data flows through your system and uses only one part at a time at each stage. Many parts in a function may be a code smell for bad, non-reconfigurable architecture and one-part-per-function is a sign of a good design. If you do need multiple components in a function, combine them into a single map with generic keys.
  * Good: `(myfunction datomic-conn args)` - Only the datomic connection is used here.
  * Bad: `(myfunction datomic-conn sql-conn args)` - Two different parts are used in two positions.
  * Better: `(myfunction {:keys[datomic-conn sql-conn]} args)` - If multiple system components are needed for a single function, combine them using ig/ref to create a single, logical component with all required component.
  * Terrible: `(myfunction {:keys[project-x/datomic-connection project-x/hornetq]} arg1 arg2)` - You are conflating your system with your function. This is making your code system-specific and non-generic.
* Related to the above, parts should be combined in their initializers. Meaning, if you have some process that takes several parts (e.g. data from a db needs to flow to a queue) then do write functional logic that does the db work and another function that does the queue work then _in the part configuration_ you can put a function that does all the key renaming, function calling, etc.
* Var quote (e.g. #'(foo) vs foo)) lambdas when they are passed in as config params. This will prevent you from having to reload your system when the function changes.
* Ensure that ig/init *always* succeeds. If exceptional or failure behavior occurs (e.g. a DB is unavailable) it is ok to return a failed or broken state rather than just blowing up initialization. This facilitates investigation and recovery. For example, if you have a repl server running you could jack in and inspect the state or try to restart. If, on the other hand, the application has failed due to your exceptions you are in an irrecoverable and non-debuggable state.

### Challenges

* If you detect cycles in your dependency graph you likely want to expose your _system_ in some aspect of your API downstream. For example, you want your system viewable from your web server API code. If this happens, this is your code telling you that you need to revisit your architecture since you are trying to make your system global. Instead, do this:
  * Change your API to use only the required capabilities of your system (e.g. a db or a web server) 
  * Put the required capabilities into a component (e.g. put a db reference in your web component)
  * The handler for that component should now have the required capabilites available to it
  * Remember the above best practices when doing this. Only put what is needed in each part and design APIs to be parstbin/integrant agnostic.
* Systems in the REPL. Sometimes it can seem kludgly to have to reference a system object in the repl. You will often be wrapping your code in a let form that destructures a db connection, for example. See the following example for what I mean as well as what you can do to work in this situation.

Suppose you want to test the following function. Note that it is not system-aware, but what you have is a system.
```
(defn doit [datomic-conn arg]
  (let[db (d/db datomic-conn)]
    (d/q query db arg)))
```

The right way to test this could be one of the following:
```
;Option 1: Doing this repeatedly in the REPL might be tedious.
(let[{conn ::datomic/connection} system]
  (doit conn arg))

:Option 2: Do this once
(def datomic-conn (::datomic/connection system))
;Do this as much as you want.
(doit conn arg)
```

## Top Level Utility Methods

Currently, there are two small nses that I use pervasively when building systems that I've captured here. The first is `partsbin.core`, which declares a simple protocol along the lines of what Component does along with some helper methods for modifying the default system configuration. For a similar effort see [integrant-repl](https://github.com/weavejester/integrant-repl). The main difference I take in my approach is that I use an atom along with a simple protocol to manage the system rather than a dynamic var so that it becomes easier to localize the system (or have many) versus a single (start), (stop), etc. set of functions. If you like those other systems better, feel free to use them.

Should you want to try out my approach, it is as simple as what you see here:

```
(ns partsbin.example
  (:require [partsbin.core :refer [create start stop restart system]]
            [partsbin.datomic.api.core :as datomic]
            [partsbin.immutant.web.core :as web]
            [partsbin.clojure.java.jdbc.core :as jdbc]
            [clojure.java.jdbc :as j]
            [integrant.core :as ig]))

(defn app [{:keys [sql-conn] :as request}]
  (let [res (j/query sql-conn "SELECT 1")]
    {:status 200 :body (str "OK - " (into [] res))}))

(def config
  {::jdbc/connection    {:connection-uri "jdbc:h2:mem:mem_only"}
   ::datomic/database   {:db-uri  "datomic:mem://example"
                         :delete? true}
   ::datomic/connection {:database (ig/ref ::datomic/database)
                         :db-uri   "datomic:mem://example"}
   ::web/server         {:host         "0.0.0.0"
                         :port         3000
                         :sql-conn     (ig/ref ::jdbc/connection)
                         :datomic-conn (ig/ref ::datomic/connection)
                         :handler      #'app}})

;Note that this is a different approach than what most reloadable systems do.
(defonce sys (create config))

(start sys)
;(stop sys)
```

The second ns I provide, `partsbin.middleware`, provides an elegant and simple solution to the problem of making components available to your web handlers.

The typical web handler always boils down to something like this (be it hand-rolled, compojure, or reitit):

```
;Simple hand-rolled handler
(defn handler [request]
  {:status 200 :body "OK"})

;Compojure fanciness
(defroutes app
  (GET "/" [] (ok (html5 [:h1 "Hello World"])))
  (GET "/time" [] (str "The time is: " (time/now)))
  (route/not-found "<h1>Page not found</h1>"))
```

There may be fancy routes and so on, but ultimately it is a function  that takes a request and returns a response. This is challenging to composable apps because you may need a db or other part of your application made available to you. I've seen solutions along the lines of:

```
(defn system-handler[component]
  (routes 
    (GET "/" [] ...some sort of logic that uses the component...)))
```

This solution is pretty ugly. It still isn't clear how to inject the component into the system and your wrapped function is going to be created at every invocation.

Instead, consider this simple middleware and implementation (in this case using immutant):

```
(defn wrap-component [handler component]
  (fn [request] (handler (into component request))))

(defmethod ig/init-key ::server [_ {:keys [handler host port] :as m}]
  (immutant/run (wrap-component handler m) {:host host :port port}))
```

This solution pours the request into your configured component (the map used to create the server). I pour the request into the component so that if there are key collisions the request wins. 

The result of this middleware is that when you declare keys like `:datomic-conn (ig/ref  ::datomic/connection)` the referenced component is now injected into your request and is available to your handler. Awesome, right!

Here's what it looks like:
``` 
(defn handler [{:keys [datomic-conn] :as request}]
  (let [db (d/db datomic-conn)]
    {:status 200 :body (d/q some-query db))}))
```

Notice that this follows the guiding principles outlined above. This is just a simple function in which I expect a request with a connection that I specified. There's no particular knowledge of partsbin or anything else. I just write the function and let some other aspect of the system worry about creating the right request.

## Implementations

An aim of partsbin is to provide an ever-accreting set of implementations of 'ig/init-key', 'ig/halt-key!' for a variety of libraries that you might use in your systems. To ensure that this is done thoughtfully the following strategy has been taken:

* For each wrapped library, a package is created under partsbin.package.of.wrapped.lib, where the path corresponds to the same ns that would be imported from the library itself. For example, to obtain a jdbc connection using `clojure.java.jdbc`, the function `clojure.java.jdbc/get-connection` is called from the `clojure.java.jdbc` ns. In this case, I would create the package `partsbin.clojure.java.jdbc`.
* Within the package I use versioned namespaces for each implementation. The following namespaces will be used:
  * alpha: The alpha namespace is the latest non-stable version of the implementation. It is subject to change at any time.
  * core: The core namespace contains a link to the latest stable (unless alpha is all that exists) version of the api. This is done by deriving the keys from the latest stable api in core.
  * vX, where X is the latest major version of the API being wrapped. For example, `clojure.java.jdbc.v0` corresponds to the latest released major version of this library (0.7.10 as of this writing). This version will not be produced until it is deemed stable. If a stable API version is determined to be wanting, a new ns should be created (vX_0 or whatever). If it corresponds to the latest version of the API, the core derivation should also be updated.
  * Note that this has a few consequences. Alpha can be unstable. Core can change but should be relatively stable. vX should always be stable/accrete-only. If you want guaranteed behavior, use a vX ns or just vendor and modify your own implementation.
* I often provide a basic config in each core ns as well as a comment that contains an example to launch the config, this making each implementation self-documenting.
* All libraries are set to :scope "provided" in the project.clj file. This is done to prevent users of the project from depending on every single part. This does mean that you, the user, must identify which parts you want and which jars you must include in your path to make everything work. Any ns that you require must have its dependencies explicitly included by you.
* I will happily examine PRs if you want to add more part implementations.

<hr/>

Copyright © 2019 Mark Bastian

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
