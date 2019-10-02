(ns partsbin.clojure.java.jdbc.latest
  (:require [partsbin.clojure.java.jdbc.v0 :as jdbc]))

(derive ::connection ::jdbc/connection)
