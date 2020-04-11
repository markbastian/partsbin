(ns partsbin.aleph.http.core
  (:require [partsbin.aleph.http.alpha :as web]
            [integrant.core :as ig]))

(derive ::server ::web/server)

(def config {::server {:host    "0.0.0.0"
                       :port    3000
                       :handler (constantly {:status 200 :body "OK"})}})

(comment
  (defonce system (ig/init config))
  (ig/halt! system))