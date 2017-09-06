(ns cdflow.controller
  (:require [clojure.java.io :as io])
  (:import [javafx.event ActionEvent]
           [javafx.stage Stage DirectoryChooser FileChooser StageStyle Window Modality]
           [javafx.application Platform]
           [javafx.scene Scene]
           [javafx.scene.input KeyEvent KeyCode]
           [javafx.scene.control TreeView TreeItem]
           [java.awt Desktop])

  (:gen-class
    :methods [[onLoad [javafx.event.ActionEvent] void]
              [onOpen [javafx.event.ActionEvent] void]]
    ))

(def current-stage (atom nil))

(defn showBranches [tree]
  (.setRoot tree (TreeItem. "Capo")))

(defn -onLoad [this ^ActionEvent event]
  (let [source (.getSource event)
        scene (.getScene source)
        editor (.lookup scene "#editor")
        webview (.lookup scene "#webview")
        menuBar (.lookup scene "#menuBar")
        engine (.getEngine webview)]

    (.set (.useSystemMenuBarProperty menuBar) true)
    (.load engine (.toString (io/resource "tree/index.html")))
    (.setRoot (.lookup scene "#branches") nil)))

(defn -onOpen [this ^ActionEvent event]
  (let [chooser (doto (DirectoryChooser.)
                      (.setTitle "Import"))
        repo (.showDialog chooser (Stage.))
        scene (->> event
                   .getSource
                   .getParentPopup
                   .getOwnerWindow
                   .getScene)]

    (showBranches (.lookup scene "#branches"))))