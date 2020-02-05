# Random User API Consumption
Application to consume random user endpoint.
It works by rendering a user fetched from the api. The app has the feature to explore other users or to go back and look at a previously fetched user.

Note: To achieve this is required that the users have an unique id. Sometimes the api returns empty strings for user Id. In those cases the response is discarded and a toast mesasge is displayed


# Arquitecture

## Domain
The inner-most layer is domain, with all the business logic. No android classes are use here
Entities and Repository definitions live here

## Data
This is the layer with the concrete implementations for repositories.
Current implementations use in-memory storage.
As all repositories implement contract clases (Dependency inversion), those repository implementations could be easily replaced with others with different data sources like Room, Realm, etc...

## App
This layer contains all the presentation logic. Here is where the android stuff lives in. Activities, Fragments, Adapters etc...
MVVM presentation pattern is used.
The view will send intent actions to the VM and tha VM will post states to be rendered by the view.

# Library Stack
* Dager2: For dependency Injection
* Retrofit/OkHttp/Gson: For service consuption
* Glide: For image loading


Working Example. (Takes a while to load)
----
![](randomuser.gif)
