package com.example

import geb.Page

class HomePage extends Page {

    static url = "/"

    static at = { title == "Welcome to Grails" }

    // If another page object extends this one, the content definitions will be merged
    // https://www.gebish.org/manual/current/#pages-inheritance
    static content = {
        welcomeHeader { $('[data-test-id="welcome-header"]') }
    }
}