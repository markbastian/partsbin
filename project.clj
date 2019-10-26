(defproject markbastian/partsbin "0.1.2-SNAPSHOT"
  :description "A project for creating functional, data-driven systems."
  :url "https://github.com/markbastian/partsbin"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}

  :exclusions [clj-time
               commons-codec
               commons-fileupload
               commons-io
               joda-time
               primitive-math
               com.google.code.findbugs/jsr305
               com.google.guava/guava
               org.clojure/tools.logging
               org.clojure/tools.reader
               org.jboss.logging/jboss-logging
               org.slf4j/slf4j-api
               ring/ring-codec
               ring/ring-core]

  :dependencies [[org.clojure/clojure "1.10.1"]
                 [integrant "0.7.0"]
                 [com.taoensso/timbre "4.10.0"]
                 [datascript "0.18.7" :scope "provided"]
                 [etaoin "0.3.5" :scope "provided"]
                 [hawk "0.2.11" :scope "provided"]
                 [hikari-cp "2.9.0" :scope "provided"]
                 [clojure.jdbc/clojure.jdbc-c3p0 "0.3.3" :scope "provided"]
                 [com.datomic/datomic-free "0.9.5697" :scope "provided"]
                 [com.h2database/h2 "1.4.200" :scope "provided"]
                 [com.novemberain/monger "3.5.0" :scope "provided"]
                 [factual/durable-queue "0.1.6" :scope "provided"]
                 [org.clojure/java.jdbc "0.7.10" :scope "provided"]
                 [org.immutant/scheduling "2.1.10" :scope "provided"]
                 [org.immutant/web "2.1.10" :scope "provided"]
                 [ring/ring-jetty-adapter "1.7.1" :scope "provided"]
                 [seancorfield/next.jdbc "1.0.9" :scope "provided"]
                 ;Just to prevent version conflicts
                 [org.clojure/tools.reader "1.3.2"]
                 [clj-time "0.15.2" :scope "provided"]
                 [commons-codec "1.13" :scope "provided"]
                 [commons-fileupload "1.4" :scope "provided"]
                 [commons-io "2.6" :scope "provided"]
                 [joda-time "2.10.5" :scope "provided"]
                 [primitive-math "0.1.6" :scope "provided"]
                 [com.google.code.findbugs/jsr305 "3.0.2" :scope "proivided"]
                 [com.google.guava/guava "23.0" :scope "provided"]
                 [org.clojure/tools.logging "0.5.0" :scope "provided"]
                 [org.jboss.logging/jboss-logging "3.4.1.Final" :scope "provided"]
                 [org.slf4j/slf4j-api "1.7.28" :scope "provided"]
                 [ring/ring-codec "1.1.2" :scope "provided"]
                 [ring/ring-core "1.7.1" :scope "provided"]]
  :repositories [["releases" {:url "https://repo.clojars.org" :creds :gpg}]])
