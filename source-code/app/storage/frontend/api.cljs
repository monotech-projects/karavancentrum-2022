
(ns app.storage.frontend.api
    (:require [app.storage.frontend.alias-editor.effects]
              [app.storage.frontend.capacity-handler.subs]
              [app.storage.frontend.directory-creator.effects]
              [app.storage.frontend.directory-creator.events]
              [app.storage.frontend.file-uploader.effects]
              [app.storage.frontend.file-uploader.events]
              [app.storage.frontend.file-uploader.side-effects]
              [app.storage.frontend.file-uploader.subs]
              [app.storage.frontend.media-browser.effects]
              [app.storage.frontend.media-browser.lifecycles]
              [app.storage.frontend.media-menu.effects]
              [app.storage.frontend.media-picker.subs]
              [app.storage.frontend.media-selector.effects]
              [app.storage.frontend.media-selector.events]
              [app.storage.frontend.media-selector.subs]
              [app.storage.frontend.media-viewer.effects]
              [app.storage.frontend.media-viewer.events]
              [app.storage.frontend.media-viewer.subs]
              [app.storage.frontend.media-picker.views :as media-picker.views]))

;; -- Redirects ---------------------------------------------------------------
;; ----------------------------------------------------------------------------

; app.storage.frontend.media-picker.views
(def media-picker media-picker.views/element)
