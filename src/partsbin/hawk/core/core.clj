(ns partsbin.hawk.core.core
  (:require [integrant.core :as ig]
            [partsbin.hawk.core.alpha :as hawk]))

(derive ::watch ::hawk/watch)

(def config
  {::hawk/watch {:example-key :example-value
                 :opts        {:a 1 :b 2}
                 :groups      [{:paths   ["src"]
                                :context (constantly {:context 42})
                                :handler (fn example-handler [ctx e]
                                           (println "event: " e)
                                           (println "context: " ctx)
                                           ctx)}]}})

(comment
  (def sys (ig/init config))
  (ig/halt! sys))