
(ns site.modules.navbar
  (:require
   [x.app-core.api :as a :refer [r]]
   [x.app-elements.api :as elements]
   [x.app-components.api :as components]
   [x.app-environment.api :as environment]

   [dom.api :as dom]
   [reagent.api :as reagent :refer [lifecycles]]

   [site.modules.sidebar.views :as sidebar]))

;; -----------------------------------------------------------------------------
;; ---- Utils ----

(defn get-layout! [db threshold]
  (let [viewport-width (r environment/get-viewport-width db)
        change?        (> threshold viewport-width)]
    (if change?
      "mobile"
      "desktop")))

;; ---- Utils ----
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; ---- Subscriptions ----

(defn get-view-props [db [_ {:keys [threshold] :or {threshold 400}}]]
  {:layout (get-layout! db threshold)
   :menu {:in (get-in db [::menu :in] false)}})

(a/reg-sub ::get-view-props get-view-props)

;; ---- Subscriptions ----
;; -----------------------------------------------------------------------------

;; -----------------------------------------------------------------------------
;; ---- Components ----

(defn logo-img [{:keys [src width]
                 :or {width "auto"}}]
  [:div#logo {:style {"--logo-width" width}}
   [:a {:href "/"}
    [:img {:src src}]]])

(defn nav-logo [logo]
  (if (map? logo)
    [logo-img logo]
    logo))

(defn menu [items]
  [:div#menu
   (map-indexed (fn [idx item]
                  ^{:key (str "Navbar-" idx)}
                  [:<> item])
     items)])

(defn open-button [layout]
  (if (= "mobile" layout)
    [:div#navbar--menu-btn
     [:button {:on-click #(a/dispatch [:open-sidebar! :nav-menu])}
      [elements/icon {:icon :menu :size :xl}]]]))

(defn get-alignment [{:keys [logo align-x]}]
  (if-not (empty? logo)
    "space-between"
    (let [options {:left   "flex-start"
                   :center "center"
                   :right  "flex-end"}]
      (get options align-x "flex-start"))))

(defn navbar-desktop [config items {:keys [layout] :as view-props}]
  [:nav#navbar {:data-layout layout}
   [:div#navbar--container {:style {:justify-content (get-alignment config)}}
    [nav-logo (:logo config)]
    [menu items]]])

(defn navbar-mobile [config items {:keys [layout] :as view-props}]
  [:<>
   [:nav#navbar {:data-layout layout}
    [:div#navbar--container
     [nav-logo (:logo config)]
     [open-button layout]]
    [sidebar/view {:id :nav-menu}
     [menu items]]]])

(defn navbar [config items {:keys [layout] :as view-props}]
  [:<>
   [:div#navbar-sensor {:style {:height "1px" :position "absolute" :top 0}}]
   (if (= "mobile" layout)
     [navbar-mobile config items view-props]
     [navbar-desktop config items view-props])])

(defn view [config & items]
  (lifecycles
    {:component-did-mount #(dom/setup-intersection-observer!
                             (dom/get-element-by-id "navbar-sensor")
                             (fn [intersecting?]
                               (environment/set-element-attribute! "navbar" "data-top" intersecting?)
                               (environment/set-element-attribute! "scroll-icon" "data-show" intersecting?)))
     :reagent-render
     (fn [config & items]
       (let [view-props @(a/subscribe [::get-view-props config])]
         [navbar config items view-props]))}))

;; ---- Components ----
;; -----------------------------------------------------------------------------