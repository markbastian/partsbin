(ns partsbin.examples.immutant
  (:require [partsbin.system :refer [create-system]]
            [partsbin.immutant.web.core :as web]
            [clojure.pprint :as pp]))

(defn handler [request]
  {:status 200
   :body   (with-out-str (pp/pprint request))})

(defn config []
  {::web/server {:host    "0.0.0.0"
                 :port    3000
                 :handler #'handler}})

(macroexpand
  '(create-system (config)))

(create-system (config))