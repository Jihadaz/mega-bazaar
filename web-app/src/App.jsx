import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import ProductList from './pages/ProductList';
import ProductDetails from './pages/ProductDetails';
import ProductForm from './pages/ProductForm';
import CategoryForm from './pages/CategoryForm';
import OrderForm from './pages/OrderForm';
import { Link } from 'react-router-dom';
import Layout from './pages/Layout';
import Home from './pages/Home';
import OrderSuccess from './pages/OrderSuccess';
import ProductCrud from './pages/ProductCrud';

const App = () => {
  return (
    <Router>
        {/* <header className="bg-blue-600 p-4 text-white">
          <h1 className="text-2xl font-bold">Mega Bazaar</h1>
          <nav>
            <Link
              to="/category/new"
              className="bg-white text-blue-600 px-4 py-2 rounded hover:bg-gray-200"
            >
              Add Category
            </Link>
          </nav>
        </header> */}
          <Routes>
            <Route path="/" element={<Layout />} >

              <Route index element={<Home />} />
              <Route path="products" element={<ProductList />} /> 
              <Route path="product/:id" element={<ProductDetails />} />
              <Route path="orders/new" element={<OrderForm />} /> 
              <Route path="order-sumbitted" element={<OrderSuccess />} /> 

              <Route path="products/manage" element={<ProductCrud />} />
              <Route path="product/new" element={<ProductForm />} />
              <Route path="category/new" element={<CategoryForm />} />
            </Route>
          </Routes>
    </Router>
  );
};

export default App;
