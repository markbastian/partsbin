(ns partsbin.core
  (:require [partsbin.system :refer [create start stop restart system]]
            [partsbin.immutant.web :as web]
            [partsbin.clojure.java.jdbc :as jdbc]
            [clojure.java.jdbc :as j]))

(defn app [request] {:status 200 :body "OK"})

(def config
  {::jdbc/connection
   {:connection-uri "jdbc:h2:mem:mem_only "}
   ::web/server
   {:host    "0.0.0.0"
    :port    3000
    :handler #'app}})

(defonce sys (create config))

(comment
  (let [{:keys [::jdbc/connection]} (system sys)]
    (j/query connection "SELECT 1")))