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
    }
}

rootProject.name = "Android_Component_02_Fragment_starter"
include (":a1_app")
include (":a2_lifecycle")
include (":b1_interface_comm")
include (":b2_bundle")
include (":b3_fragment_result")
include (":c1_tab_and_others")
include (":c2_bottom_navi")
