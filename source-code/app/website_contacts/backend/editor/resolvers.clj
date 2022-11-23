
(ns app.website-contacts.backend.editor.resolvers
    (:require [app.website-contacts.backend.handler.config :as handler.config]
              [com.wsscode.pathom3.connect.operation       :refer [defresolver]]
              [io.api                                      :as io]
              [pathom.api                                  :as pathom]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn- get-content-f
  ; @param (map) env
  ; @param (map) resolver-props
  ;
  ; @return (map)
  [_ _]
  (io/read-edn-file handler.config/WEBSITE-CONTACTS-FILEPATH))

(defresolver get-content
             ; @param (map) env
             ; @param (map) resolver-props
             ;
             ; @return (namespaced map)
             ;  {:website-contacts.editor/get-content (map)}
             [env resolver-props]
             {:website-contacts.editor/get-content (get-content-f env resolver-props)})

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @constant (functions in vector)
(def HANDLERS [get-content])

(pathom/reg-handlers! ::handlers HANDLERS)
