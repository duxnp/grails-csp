package com.example

import grails.gorm.transactions.Rollback
import grails.testing.mixin.integration.Integration

import geb.Page
import geb.spock.*

/**
 * See https://www.gebish.org/manual/current/ for more instructions
 */
@Integration
@Rollback
class GrailsCspSpec extends GebSpec {

    void "test inline style and script"() {
        Page homePage

        when: "the home page is visited"
        homePage = to HomePage

        then: "the title is correct"
        at HomePage

        and: "the welcome header is displayed"
        homePage.welcomeHeader.css("display") != "none"

        and: "the welcome header color is red"
        homePage.welcomeHeader.css("color") == "rgb(255, 0, 0)"
    }

}
