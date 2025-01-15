import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import instance from '../services/api';

const ProductList = () => {
  const [products, setProducts] = useState([]);

  useEffect(() => {
    fetchProducts();
  }, []);

  const fetchProducts = async () => {
    try {
      const response = await instance.get('/products');
      console.log('API Response:', response.data); // Log response
      setProducts(response.data.products || []); // Adjust based on actual response structure
    } catch (error) {
      console.error('Error fetching products:', error);
      setProducts([]); // Fallback to an empty array on error
    }
  };

  return (
    <>
    <h1 className="text-center mt-11 font-display text-4xl md:text-5xl lg:text-6xl xl:text-7xl font-extrabold text-lime-900 tracking-tight leading-tight mb-8 md:mb-10">
          Our Products
        </h1>
    <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4 px-4 py-8">
      {products.map((product) => (
        <div key={product.id} className="product-card bg-white rounded shadow-md overflow-hidden transition-all duration-300 hover:shadow-lg">
          <Link to={`/product/${product.id}`}>
            <img src={`http://localhost:8080/api/products/images/get/${product.imgPath}`} alt={product.label} className="w-full h-64 object-cover" />
          </Link>
          <div className="p-4">
            <h2 className="text-xl font-semibold text-gray-800">{product.label}</h2>
            <p className="text-gray-600 mb-2">{product.description}</p>
            <div className="flex items-center justify-between">
              <p className="text-gray-700 font-bold">Price: ${product.price}</p>
              <Link to={`/product/${product.id}`} className="text-lime-500  mt-2">
                View Details
              </Link>
            </div>
          </div>
        </div>
      ))}
    </div>
    </>
    
  );
};

export default ProductList;