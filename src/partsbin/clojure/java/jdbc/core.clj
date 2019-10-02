(ns partsbin.clojure.java.jdbc.core
  (:require [partsbin.clojure.java.jdbc.v0 :as jdbc]))

(derive ::connection ::jdbc/connection)
