(ns partsbin.ring.adapter.jetty.core
  (:require [partsbin.ring.adapter.jetty.alpha :as web]
            [integrant.core :as ig]))

(derive ::server ::web/server)


(def config {::server {:host    "0.0.0.0"
                       :port    3000
                       :handler (constantly {:status 200 :body "OK"})}})

(comment
  (def system (ig/init config))
  (ig/halt! system))
