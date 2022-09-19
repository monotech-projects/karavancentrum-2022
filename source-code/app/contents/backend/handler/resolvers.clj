
(ns app.contents.backend.handler.resolvers
    (:require [com.wsscode.pathom3.connect.operation :refer [defresolver]]
              [mid-fruits.candy                      :refer [return]]
              [mongo-db.api                          :as mongo-db]
              [pathom.api                            :as pathom]
              [x.server-user.api                     :as user]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-content-f
  ; @param (map) env
  ; @param (map) resolver-props
  ;
  ; @return (namespaced map)
  [{:keys [request] :as env} _]
  (let [content-id (pathom/env->param env :content-id)]
       (if-let [{:content/keys [body visibility]} (mongo-db/get-document-by-id "contents" content-id)]
               (case visibility :private (if (user/request->authenticated? request)
                                             (return body))
                                :public      (return body)))))

(defresolver get-content
             ; @param (map) env
             ; @param (map) resolver-props
             ;  {:content-id (string)}
             ;
             ; @return (map)
             ;  {:contents.content-handler/get-content (namespaced map)}
             [env resolver-props]
             {:contents.content-handler/get-content (get-content-f env resolver-props)})

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @constant (functions in vector)
(def HANDLERS [get-content])

(pathom/reg-handlers! ::handlers HANDLERS)