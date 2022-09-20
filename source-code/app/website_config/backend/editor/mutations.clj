
(ns app.website-config.backend.editor.mutations
    (:require [app.website-config.backend.handler.config :as handler.config]
              [com.wsscode.pathom3.connect.operation     :as pathom.co :refer [defmutation]]
              [mid-fruits.candy                          :refer [return]]
              [pathom.api                                :as pathom]
              [server-fruits.io                          :as io]
              [x.server-core.api                         :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn save-content-f
  ; @param (map) env
  ; @param (map) mutation-props
  ;  {:content (map)}
  ;
  ; @return (map)
  [env {:keys [content]}]
  (io/write-edn-file! handler.config/WEBSITE-CONFIG-FILEPATH content)
  ;(a/dispatch-fx [:core/import-website-config!])
  (return content))

(defmutation save-content!
             ; @param (map) env
             ; @param (map) mutation-props
             ;  {:content (map)}
             ;
             ; @return (map)
             [env mutation-props]
             {::pathom.co/op-name 'website-config/save-content!}
             (save-content-f env mutation-props))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @constant (functions in vector)
(def HANDLERS [save-content!])

(pathom/reg-handlers! ::handlers HANDLERS)
