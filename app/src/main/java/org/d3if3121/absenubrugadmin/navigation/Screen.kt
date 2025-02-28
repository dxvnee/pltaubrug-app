package org.d3if3121.absenubrugadmin.navigation

sealed class Screen(val route: String) {
    data object Login: Screen("LoginPage")
    data object Register: Screen("RegisterPage")
    data object Home: Screen("HomePage")
    data object Project: Screen("ProjectPage")
    data object Employee: Screen("EmployeePage")
    data object EmployeeDetail: Screen("EmployeeDetailPage")
    data object Profile: Screen("ProfilePage")
}
