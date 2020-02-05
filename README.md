# Random User API Consumption

Application with to consume random user endpoint

It uses the following architectural principles:
* Layered applciation. There are 3 main layers: Domain, Data, and App

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
![](randomuser.gif)
