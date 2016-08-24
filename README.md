Play EmberJs Seed
===

This is a seed project which includes a `Ember Js` ui application and includes ember-cli's workflow into plays workflow.

###Thing it does out of the box
* When ```activator run``` is executed it does the following:
    * Runs ```npm install``` and ```bower install``` in the ```ui``` directory.
    * Starts node server(```ember serve```) to start serving ui application in port number: 4200(default).
    * unmanagedResources includes ```ui/dist``` and hence the content is included as Asset.
    * `/` is configured to redirect to ```localhost:4200```(default) in dev mode. This is done so that the productivity of
    ember-cli is maintained. (live reload works :sparkles:)

* When the application is packaged
    * Executes ```ember build --environment=production``` in the ```ui``` directory before packaging of the play application happens.

**Note:** When `activator run` is executed, the ember server is started with XHR proxy set to the play application. So, when XHR
 call is sent from UI, the call gets proxied by the ember server to the play application.

Controllers
===
- EmberProxyController.scala:
  Handles the `/` route in `Dev` and `Prod` mode. `index` action redirects to ```localhost:4200```(default) in dev mode and in prod mode
  serves the `index.html` file of the ember application

- DummyController.scala:
  Used as an example. Should be removed in actual project

Configuration
===

Set `uiAppDirectory` settings in `build.sbt` to any value to set the directory containing the ember application. Defaults to `ui`.
This is relative to `baseDirectory` settings.

```
uiAppDirectory := 'custom-ui'
```

Set `uiServerPort` settings in `build.sbt` to change the port where the ember application starts up in dev mode. Defaults to 4200.
If you set this value then `ember.application.port` configuration has to be added in `application.conf`. This is required because at
runtime `EmberProxyController` has to know where the ember application is configured to run.

In `build.sbt` -
```
uiServerPort := 4999
```

In `application.sbt` -
```
# Development time option used to redirect to the ember application served through the node server
ember {
  # Application port in which the ember server has been started in dev mode. Not required in prod or test.
  # By default this port is set to 4200 if 'uiServerPort' settings has not been overriden in build.sbt
  # If 'uiServerPort' is set to some other port then this property has to be chnaged accordingly
  application.port = 4999
}
```

How to use
===
Assumption node and ember-cli is installed in the machine.

Steps
- Clone this repository from github.
- Execute `activator run`

LICENSE
===
This software is licensed under the Apache 2 license, quoted below.

Licensed under the Apache License, Version 2.0 (the "License"); you may not use this project except in compliance with
the License. You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0.

Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an
"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific
language governing permissions and limitations under the License.