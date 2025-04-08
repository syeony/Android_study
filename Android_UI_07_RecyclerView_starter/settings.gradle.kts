pluginManagement {
    repositories {
        gradlePluginPortal()
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io") // RecyclerViewSwipeDecorator
    }
}

rootProject.name = "Android_UI_07_RecyclerView_starter"
include (":a1_listview")
include (":a2_listview_customrow")
include (":a3_listview_customrow_holder")
include (":a4_listview_to_recylerview")
include (":a5_recylerview")
