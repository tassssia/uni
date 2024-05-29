import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import UserMain from "./pages/userMain/UserMain";
import Login from "./pages/login/Login";
import Registration from "./pages/registration/Registration";
import {createBrowserRouter, RouterProvider} from 'react-router-dom';
import Cart from "./pages/card/Cart";
import Manager from "./pages/manager/Manager";
import TourCreation from "./pages/manager/TourCreation";
import {Auth0Provider} from "@auth0/auth0-react";

const root = ReactDOM.createRoot(
    document.getElementById('root') as HTMLElement
);

const router = createBrowserRouter([
    {
        path: '/',
        element: <UserMain/>
    },
    {
        path: '/login',
        element: <Login/>
    },
    {
        path: '/register',
        element: <Registration/>
    },
    {
        path: '/main',
        element: <UserMain/>
    },
    {
        path: '/cart',
        element: <Cart/>
    },
    {
        path: '/manager',
        element: <Manager/>
    },
    {
        path: '/manager/new-tour',
        element: <TourCreation/>
    }
]);


root.render(
    <React.StrictMode>
        <Auth0Provider
            domain='dev-yiycc650cnpa0puz.us.auth0.com'
            clientId='tbxYynSZQc2nyCwJFC4Pb0SH4nJ3MCiG'
            authorizationParams={{
                redirect_uri: window.location.origin
            }}
        >
            <RouterProvider router={router}/>
        </Auth0Provider>
    </React.StrictMode>
);