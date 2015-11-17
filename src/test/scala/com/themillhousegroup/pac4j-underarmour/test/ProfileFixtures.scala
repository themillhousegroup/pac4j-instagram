package com.themillhousegroup.pac4junderarmour.test

object ProfileFixtures {

  val fullProfile = """
{
    "last_name": "McLastName",
    "weight": 74.84274105,
    "communication": {
        "promotions": false,
        "newsletter": false,
        "system_messages": false
    },
    "height": 1.778,
    "hobbies": "",
    "id": 512262,
    "date_joined": "2008-05-12T21:23:39+00:00",
    "first_name": "FirstName",
    "display_name": "FirstName 'Display' McLastName",
    "introduction": "",
    "display_measurement_system": "metric",
    "last_login": "2012-09-09T03:24:19+00:00",
    "location": {
        "country": "AU",
        "region": "Vic",
        "locality": "Locality",
        "address": "3476 Address-Field Street"
    },
    "_links": {
        "stats": [
            {
                "href": "/v7.1/user_stats/512262/?aggregate_by_period=month",
                "id": "512262",
                "name": "month"
            },
            {
                "href": "/v7.1/user_stats/512262/?aggregate_by_period=year",
                "id": "512262",
                "name": "year"
            },
            {
                "href": "/v7.1/user_stats/512262/?aggregate_by_period=day",
                "id": "512262",
                "name": "day"
            },
            {
                "href": "/v7.1/user_stats/512262/?aggregate_by_period=week",
                "id": "512262",
                "name": "week"
            },
            {
                "href": "/v7.1/user_stats/512262/?aggregate_by_period=lifetime",
                "id": "512262",
                "name": "lifetime"
            }
        ],
        "privacy": [
            {
                "href": "/v7.1/privacy_option/0/",
                "id": "0",
                "name": "workout_music"
            },
            {
                "href": "/v7.1/privacy_option/1/",
                "id": "1",
                "name": "workout"
            },
            {
                "href": "/v7.1/privacy_option/1/",
                "id": "1",
                "name": "profile"
            },
            {
                "href": "/v7.1/privacy_option/3/",
                "id": "3",
                "name": "activity_feed"
            },
            {
                "href": "/v7.1/privacy_option/0/",
                "id": "0",
                "name": "bodymass"
            },
            {
                "href": "/v7.1/privacy_option/1/",
                "id": "1",
                "name": "food_log"
            },
            {
                "href": "/v7.1/privacy_option/3/",
                "id": "3",
                "name": "email_search"
            },
            {
                "href": "/v7.1/privacy_option/1/",
                "id": "1",
                "name": "route"
            },
            {
                "href": "/v7.1/privacy_option/0/",
                "id": "0",
                "name": "sleep"
            }
        ],
        "image": [
            {
                "href": "/v7.1/user_profile_photo/512262/",
                "id": "512262",
                "name": "user_profile_photo"
            }
        ],
        "documentation": [
            {
                "href": "https://developer.underarmour.com/docs/v71_User"
            }
        ],
        "deactivation": [
            {
                "href": "/v7.1/user_deactivation/"
            }
        ],
        "user_achievements": [
            {
                "href": "/v7.1/user_achievement/?user=512262"
            }
        ],
        "friendships": [
            {
                "href": "/v7.1/friendship/?from_user=512262"
            }
        ],
        "workouts": [
            {
                "href": "/v7.1/workout/?user=512262&order_by=-start_datetime"
            }
        ],
        "self": [
            {
                "href": "/v7.1/user/512262/?access_token=64ce802a06585c9cfe172860ad24c6704f6fdd2c",
                "id": "512262"
            }
        ]
    },
    "email": "me@myemail.com",
    "goal_statement": null,
    "username": "myusername",
    "sharing": {
        "twitter": false,
        "facebook": false
    },
    "last_initial": "M.",
    "preferred_language": "en-US",
    "gender": "M",
    "time_zone": "Australia/Melbourne",
    "birthdate": "1987-09-17",
    "profile_statement": ""
}
	"""

}
