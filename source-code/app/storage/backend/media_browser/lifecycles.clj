
(ns app.storage.backend.media-browser.lifecycles
    (:require [plugins.item-browser.api]
              [x.server-core.api :as a]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(a/reg-lifecycles!
  ::lifecycles
  {:on-server-boot [:item-browser/init-browser! :storage.media-browser
                                                {:base-route      "/@app-home/storage"
                                                 :collection-name "storage"
                                                 :handler-key     :storage.media-browser
                                                 :item-namespace  :media
                                                 :on-route        [:storage.media-browser/load-browser!]
                                                 :route-title     :storage}]})
