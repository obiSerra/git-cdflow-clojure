(ns cdflow.core
  (:require [clojure.java.io :as io]
            [cdflow.gui]
            [cdflow.server :as server])
  (:import [javafx.application Application])

  (:gen-class :name cdflow.core))

(defn start-server []
  (server/start))

(defn -main [& args]
  (start-server)
  (Application/launch cdflow.gui (into-array String args))
  )
