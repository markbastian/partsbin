(defproject markbastian/partsbin "0.1.3-SNAPSHOT"
  :description "A project for creating functional, data-driven systems."
  :url "https://github.com/markbastian/partsbin"
  :license {:name "Eclipse Public License"
            :url  "http://www.eclipse.org/legal/epl-v10.html"}

  :managed-dependencies [[aleph "0.4.6"]
                         [args4j "2.33"]
                         [byte-streams "0.2.4"]
                         [clj-time "0.15.2"]
                         [commons-codec "1.14"]
                         [commons-fileupload "1.4"]
                         [commons-io "2.6"]
                         [datascript "0.18.11"]
                         [etaoin "0.3.6"]
                         [hawk "0.2.11"]
                         [hikari-cp "2.11.0"]
                         [integrant "0.8.0"]
                         [joda-time "2.10.6"]
                         [primitive-math "0.1.6"]
                         [clojure.jdbc/clojure.jdbc-c3p0 "0.3.3"]
                         [com.datomic/datomic-free "0.9.5697"]
                         [com.fasterxml.jackson.core/jackson-core "2.11.0"]
                         [com.fzakaria/slf4j-timbre "0.3.19"]
                         [com.google.code.findbugs/jsr305 "3.0.2"]
                         [com.google.errorprone/error_prone_annotations "2.3.4"]
                         [com.google.guava/guava "23.0"]
                         [com.google.guava/guava "28.2-jre"]
                         [com.google.javascript/closure-compiler-externs "v20200406"]
                         [com.google.javascript/closure-compiler-unshaded "v20200406"]
                         [com.h2database/h2 "1.4.200"]
                         [com.novemberain/monger "3.5.0"]
                         [com.taoensso/encore "2.119.0"]
                         [com.taoensso/timbre "4.10.0"]
                         [factual/durable-queue "0.1.6"]
                         [io.replikativ/datahike "0.2.1"]
                         [juxt/crux-core "19.09-1.5.0-alpha"]
                         [org.clojure/clojure "1.10.1"]
                         [org.clojure/clojurescript "1.10.742"]
                         [org.clojure/core.async "1.1.587"]
                         [org.clojure/core.cache "1.0.207"]
                         [org.clojure/core.memoize "1.0.236"]
                         [org.clojure/data.codec "0.1.1"]
                         [org.clojure/java.jdbc "0.7.11"]
                         [org.clojure/tools.logging "1.1.0"]
                         [org.clojure/tools.reader "1.3.2"]
                         [org.immutant/scheduling "2.1.10"]
                         [org.immutant/web "2.1.10"]
                         [org.jboss.logging/jboss-logging "3.4.1.Final"]
                         [org.slf4j/slf4j-api "1.7.30"]
                         [ring/ring-codec "1.1.2"]
                         [ring/ring-core "1.8.0"]
                         [ring/ring-jetty-adapter "1.8.0"]
                         [seancorfield/next.jdbc "1.0.424"]]

  :dependencies [[org.clojure/clojure "1.10.1"]
                 [integrant "0.8.0"]
                 [com.taoensso/timbre "4.10.0"]
                 [com.fzakaria/slf4j-timbre "0.3.19" :scope "provided"]
                 [datascript "0.18.11" :scope "provided"]
                 [etaoin "0.3.6" :scope "provided"]
                 [hawk "0.2.11" :scope "provided"]
                 [hikari-cp "2.11.0" :scope "provided"]
                 [io.replikativ/datahike "0.2.1" :scope "provided"]
                 [juxt/crux-core "19.09-1.5.0-alpha" :scope "provided"]
                 [clojure.jdbc/clojure.jdbc-c3p0 "0.3.3" :scope "provided"]
                 ;[com.datomic/datomic-free "0.9.5697" :scope "provided"]
                 [com.h2database/h2 "1.4.200" :scope "provided"]
                 [com.novemberain/monger "3.5.0" :scope "provided"]
                 [factual/durable-queue "0.1.6" :scope "provided"]
                 [org.clojure/java.jdbc "0.7.11" :scope "provided"]
                 [org.immutant/scheduling "2.1.10" :scope "provided"
                  :exclusions [ch.qos.logback/logback-classic]]
                 [org.immutant/web "2.1.10" :scope "provided"]
                 [ring/ring-jetty-adapter "1.8.0" :scope "provided"]
                 [seancorfield/next.jdbc "1.0.424" :scope "provided"]
                 [aleph "0.4.6" :scope "provided"]]

  ;Note that these instructions may need to be followed to release on OS X.
  ;https://stackoverflow.com/questions/39494631/gpg-failed-to-sign-the-data-fatal-failed-to-write-commit-object-git-2-10-0
  :repositories [["releases" {:url "https://repo.clojars.org" :creds :gpg}]
                 ["snapshots" {:url "https://repo.clojars.org" :creds :gpg}]])
