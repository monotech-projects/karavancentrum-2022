
(ns app.vehicles.backend.editor.mutations
    (:require [com.wsscode.pathom3.connect.operation :as pathom.co :refer [defmutation]]
              [mongo-db.api                          :as mongo-db]
              [pathom.api                            :as pathom]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defmutation add-item!
             [{:keys [request]} {:keys [item]}]
             {::pathom.co/op-name 'vehicles.editor/add-item!}
             (mongo-db/save-document! "vehicles" item
                                      {:prototype-f #(mongo-db/added-document-prototype request :vehicle %)}))

(defmutation save-item!
             [{:keys [request]} {:keys [item]}]
             {::pathom.co/op-name 'vehicles.editor/save-item!}
             (mongo-db/save-document! "vehicles" item
                                      {:prototype-f #(mongo-db/updated-document-prototype request :vehicle %)}))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(def HANDLERS [add-item! save-item!])

(pathom/reg-handlers! ::handlers HANDLERS)