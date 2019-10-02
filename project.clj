(defproject partsbin "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [integrant "0.7.0"]
                 [org.immutant/web "2.1.10" :scope "provided"]
                 [org.clojure/java.jdbc "0.7.10" :scope "provided"]
                 [com.datomic/datomic-free "0.9.5697" :scope "provided"]
                 [com.h2database/h2 "1.4.199" :scope "provided"]
                 [com.novemberain/monger "3.1.0" :scope "provided"]])
