(ns cdflow.gui
  (:require [clojure.java.io :as io]
            [clojure.string :as str])
  (:import [javafx.application Application]
           [javafx.fxml FXMLLoader]
           [javafx.stage Stage StageBuilder]
           [javafx.scene Scene])
  (:gen-class :name cdflow.gui :extends javafx.application.Application))


(defn -start [^cdflow.gui app ^Stage stage]
  (let [root (FXMLLoader/load (io/resource "main.fxml"))
        scene (Scene. root 1242 768)]
    (doto stage
          (.setTitle "Git CDFlow")
          (.setScene scene)
          (.show))

;    (cond (str/starts-with? (str (System/getProperty "os.name")) "Mac")
;      (.set (.useSystemMenuBarProperty (.lookup scene "#menuBar") true)))
    ))

