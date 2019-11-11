(ns partsbin.next.jdbc.core
  (:require [partsbin.next.jdbc.alpha :as jdbc]
            [next.jdbc :as j]
            [next.jdbc.sql :as sql]
            [integrant.core :as ig]))

(derive ::connection ::jdbc/connection)
(derive ::datasource ::jdbc/datasource)

(def config
  {::connection {:dbtype "h2:mem" :dbname "mem_only"}
   ::datasource {:dbtype "h2:mem" :dbname "mem_only_ds"}})

(comment
  (def system (ig/init config))

  (let [{conn ::connection} system]
    (j/execute! conn ["SELECT 1"]))

  (let [{conn ::connection} system]
    (sql/query conn ["SELECT 1"]))

  (let [{conn ::datasource} system]
    (sql/query conn ["SELECT 1"]))

  (ig/halt! system))