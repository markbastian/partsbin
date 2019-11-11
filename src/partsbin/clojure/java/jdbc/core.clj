(ns partsbin.clojure.java.jdbc.core
  (:require [partsbin.clojure.java.jdbc.v0 :as jdbc]
            [clojure.java.jdbc :as j]
            [integrant.core :as ig]))

(derive ::connection ::jdbc/connection)

(def config
  {::connection {:connection-uri "jdbc:h2:mem:mem_only"}})

(comment
  (def system (ig/init config))
  (let [{conn ::connection} system]
    (j/query conn "SELECT 1"))
  (ig/halt! system))