# Monzo Code Test

This is a note explaining the reasoning behind the decision thoroughfare I followed whilst working on Monzo's code test.

## Reasoning

I will categorise my explanation according to the questions asked in the other top-level readme.

### What were your priorities, and why?
My main goal was to deliver a shippable product that can be iterated on and easily extended.

To that end, from a product perspective, my priorities were:
1. Getting articles with little to no issues.
2. Delineating articles by publication date.
3. Providing a paged experience in retrieving articles.
4. Providing offline support for getting and reading articles.
5. Provide the ability to favourite articles and view favourite articles at the top of the list.

Due to time constraints, I was only able to fully flesh out (1) and (2) and had to de-scope the rest.
- I prioritised (1) and (2) because they seemed to be the base requirement of the guardian app.
- (3) helps in providing a better user experience especially in areas with subpar internet as returning a huge bulk can end up being an expensive query that degrades said user experience. 
- (4) was a priority because I assumed our users would also be in places where there would be need to conserve data so reducing the number of network requests would
help in that endeavour.
- (5) was the last part of the spec required in completing and shipping the product.

From a technical perspective, I prioritised: 
- Offloading the instantiation and provisioning of dependencies to a separate, central entity i.e. Hilt to favour better decoupling and testability.
- Fine-tuning the Repository and data mapping approach to better abstract away details of data retrieval from higher architectural layers i.e. the presentation layer.
- Refurbishing the UI that held the list of articles and introduced a clear state management approach bound to its related ViewModel.
- Fixing any bugs/impedance to building the app successfully.

### If you had another two days, what would you have tackled next?
From a product perspective,
- I would have completed the rest of the product-perspective priorities.

and from a technical perspective, I would have:
- Completed the spec for the detail screen. 
- Completed offline reading support with a local Room DB. 
- Added a proper paging solution for the articles list.
- Added more unit tests, and some ui tests.
- Stored the API key more securely.

### What would you change about the structure of the code?
- Employ a more detailed implementation of the layered architectural pattern where for instance the repository is further abstracted from details of network/local data 
ops which would make it easier to swap out the implementation in future if need be i.e. in case of new network/DB library.
- Implement a modular approach for the project as I believe it would scale better in the case where the team members and amount of features increases.

### What bugs did you find but not fix?
- Double/multiple click events in article with several items. I didn't add proper debouncing of said actions to prevent such.

### What would you change about the visual design of the app?
I would: 
- Simplify the UI by using 1 card to represent an article as opposed to several items within one card. 
  - Subjective though it might be, I feel that approach is slightly better as it would allow us to add more info about an article to each card i.e. bookmark/favourite icons.
- Add UI for the empty states due to no internet or any other error where we cannot retrieve articles.
- Add a search bar where the user can enter their own search functionality.
- Add the ability to favourite articles from right from the home screen with each article card having a favourite icon that shows its status.  
  - Also, I'd add a menu icon to show favourited articles in a different screen rather than cluttering the main screen with multiple article types.
- Setup a much more streamlined, reusable styling initiative for the texts i.e. collective font, line-height styling.

### Approximately how long you spent on this project.
- I tried to keep within the 4 hour mark but given the components/approaches I prioritised, I ended up spending over 4 hours as my solution wasn't tenable by the 4 hour mark. 
I stopped after I made it run successfully and when I realised any further solid implementation would require significant time.
