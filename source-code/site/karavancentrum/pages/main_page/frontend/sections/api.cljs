
(ns site.karavancentrum.pages.main-page.frontend.sections.api
  (:require
    [site.karavancentrum.pages.main-page.frontend.sections.section-1 :as section-1]
    [site.karavancentrum.pages.main-page.frontend.sections.section-2 :as section-2]
    [site.karavancentrum.pages.main-page.frontend.sections.section-3 :as section-3]
    [site.karavancentrum.pages.main-page.frontend.sections.section-4 :as section-4]
    [site.karavancentrum.pages.main-page.frontend.sections.about-us  :as about-us]
    [site.karavancentrum.pages.main-page.frontend.sections.brands    :as brands]))

(def section-1 section-1/view)

(def section-2 section-2/view)

(def section-3 section-3/view)

(def section-4 section-4/view)

(def brands brands/view)

(def about-us about-us/view)
