(ns partsbin.examples.simple-web-app
  (:require [partsbin.core :refer [create start stop restart system reset-config!]]
            [partsbin.immutant.web.core :as web]
            [clojure.pprint :as pp]))

(defn app [request]
  {:status 200
   :body   (with-out-str (pp/pprint request))})

(def config
  {::web/server {:custom-key "This is a custom key"
                 :host    "0.0.0.0"
                 :port    3000
                 :handler #'app}})

(defonce sys (create config))