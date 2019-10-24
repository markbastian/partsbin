(defproject markbastian/partsbin "0.1.2-SNAPSHOT"
  :description "A project for creating functional, data-driven systems."
  :url "https://github.com/markbastian/partsbin"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [integrant "0.7.0"]
                 [org.immutant/web "2.1.10" :scope "provided"]
                 [ring/ring-jetty-adapter "1.7.1" :scope "provided"]
                 [org.clojure/java.jdbc "0.7.10" :scope "provided"]
                 [com.datomic/datomic-free "0.9.5697" :scope "provided"]
                 [com.h2database/h2 "1.4.200" :scope "provided"]
                 [com.novemberain/monger "3.5.0" :scope "provided"]
                 [hawk "0.2.11" :scope "provided"]
                 [datascript "0.18.6" :scope "provided"]
                 [hikari-cp "2.9.0" :scope "provided"]
                 [seancorfield/next.jdbc "1.0.9" :scope "provided"]
                 [clojure.jdbc/clojure.jdbc-c3p0 "0.3.3" :scope "provided"]
                 [factual/durable-queue "0.1.5" :scope "provided"]]
:repositories [["releases" {:url "https://repo.clojars.org" :creds :gpg}]])
