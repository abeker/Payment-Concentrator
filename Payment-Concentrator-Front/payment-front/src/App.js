import {BrowserRouter as Router, Switch,Route,useHistory} from 'react-router-dom';
import routes from './routes';

const RenderRoutes=(route)=>{
  const history = useHistory();
  document.title = route.title || 'App name'

  return (
    <Route
      path={route.path}
      exact
      render={(props)=><route.component {...props}/>}>
    </Route>
  )
}

function App() {
  return (
    <Router>
      <Switch>
        {routes.map((route,index)=>(
          <RenderRoutes {...route} key={index}/>
        ))}
      </Switch>
    </Router>
  );
}

export default App;
