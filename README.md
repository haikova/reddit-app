# Reader for /r/Gaming (assignment)
[![Build status](https://build.appcenter.ms/v0.1/apps/9cb2ee29-1a97-4ccd-9193-a3e722ce5293/branches/master/badge)](https://appcenter.ms)
 
This is a simple Android app that displays top posts from Reddit’s `/r/gaming` subreddit. A few notes on requirements:
- Display splash screen at the application start.
- Load posts from `http://www.reddit.com/r/gaming/top.json` and has infinity scrolling with pagination.
- Clicking on the item opens the web-browser.

[![Screenshoots](https://i.imgur.com/QGPcz3X.png)](https://github.com/olyarisu/reddit-app)

## Implementation notes
Here I’d like to highlight some aspects of the implementation that will help to better understand the used approach. The implementation is based on Paging library from Architecture components, uses Room to implement cache and Retrofit for data loading.

- Data fetching is implemented using Retrofit.
- For better UX, chrome intent is used to open links with a fallback to default browser.
- Cache is implemented using Room. First, data goes to cache and the view displays it from there and SQLite is the single source of truth in our case.
- Network failures are handled and last request is stored with possibility to retry.
- New Adaptive icon is used (not just png) for better UI on new Android phones.

## Build
The application is built and distributed via AppCenter CI, the new build is generated on each commit.

AppCenter allows to distribute builds publicly, and here’s the [download link](https://install.appcenter.ms/users/haikova/apps/reddit-gaming/distribution_groups/public%20distribution)
