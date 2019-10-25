(ns partsbin.etaoin.api.core
  (:require [partsbin.etaoin.api.alpha :as webdriver]
            [integrant.core :as ig]
            [datascript.core :as d]))

(derive ::firefox ::webdriver/firefox)
(derive ::chrome ::webdriver/chrome)

(def config {::firefox {:headless true}})

(comment
  (def sys (ig/init config))
  (ig/halt! sys))