import React from 'react';
import { Outlet, Link, useLocation } from 'react-router-dom';

// SVG components for navigation items
const HomeIcon = () => (
    <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5 mr-2" viewBox="0 0 20 20" fill="currentColor">
        <path d="M10.707 2.293a1 1 0 00-1.414 0l-7 7a1 1 0 001.414 1.414L4 10.414V17a1 1 0 001 1h2a1 1 0 001-1v-2a1 1 0 011-1h2a1 1 0 011 1v2a1 1 0 001 1h2a1 1 0 001-1v-6.586l.293.293a1 1 0 001.414-1.414l-7-7z" />
    </svg>
);

const ProductsIcon = () => (
    <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M4 6h16M4 10h16M4 14h16M4 18h16" />
    </svg>
);

const CartIcon = () => (
    <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M3 3h2l.4 2.198L7 19h10.138a1.5 1.5 0 001.482-1.758L15 12.25l-4.503 9.503m-4.502-9.503L3 3m0 0h18" />
    </svg>
);

const AboutIcon = () => (
    <svg xmlns="http://www.w3.org/2000/svg" className="h-5 w-5 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
        <path strokeLinecap="round" strokeLinejoin="round" strokeWidth={2} d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
    </svg>
);


const Layout = () => {
    const location = useLocation();

    const isActive = (path) => {
        return location.pathname === path ? 'text-white bg-lime-600' : 'hover:bg-lime-100 hover:text-lime-800';
    };

    return (
        <div className="font-body bg-gradient-to-br from-gray-100 to-gray-200 min-h-screen flex flex-col">
            <header className="bg-white/80 backdrop-blur-md py-6 px-4 shadow-lg sticky top-0 z-10">
                <div className="container mx-auto flex justify-between items-center">
                    <Link to="/" className="font-display text-xl md:text-2xl font-bold text-lime-700 tracking-wide">
                        Mega Bazaar
                    </Link>
                    <nav className="flex space-x-4 md:space-x-6">
                        <Link to="/" className={`flex items-center px-3 py-2 rounded transition-colors duration-200 ${isActive('/')}`}>
                            <HomeIcon /> Home
                        </Link>
                        <Link to="/products" className={`flex items-center px-3 py-2 rounded transition-colors duration-200 ${isActive('/products')}`}>
                            <ProductsIcon /> Products
                        </Link>
                        
                        <Link to="/about" className={`flex items-center px-3 py-2 rounded transition-colors duration-200 ${isActive('/about')}`}>
                            <AboutIcon /> About
                        </Link>
                    </nav>
                </div>
            </header>

            <main className="container mx-auto px-4 py-8 flex-grow">
                <Outlet />
            </main>

            <footer className="bg-lime-700 text-gray-200 py-4 mt-auto relative overflow-hidden">
                <div className="container mx-auto px-4 text-center relative z-10">
                    <p className="text-sm">&copy; {new Date().getFullYear()} Mega Bazaar. All rights reserved.</p>
                </div>
                <div className="absolute inset-x-0 bottom-0 h-16 bg-lime-600 -skew-y-3 origin-bottom"></div>
            </footer>
        </div>
    );
};

export default Layout;