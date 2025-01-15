import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import instance from '../services/api';

const ProductDetails = () => {
  const { id } = useParams();
  const [product, setProduct] = useState(null);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    fetchProductDetails();
  }, [id]); // Add id to dependency array

  const fetchProductDetails = async () => {
    try {
      const response = await instance.get(`/products/${id}/show`);
      setProduct(response.data);
    } catch (err) {
      console.error('Error fetching product details:', err);
      setError(err);
    }
  };

  const handleOrder = () => {
    navigate('/orders/new', { state: { product } });
  };

  if (error) {
    return (
      <div className="container mx-auto p-8 text-center">
        <h2 className="text-2xl font-bold text-red-500 mb-4">Error</h2>
        <p className="text-gray-700">Failed to load product details. Please try again later.</p>
        {/* Optionally display more detailed error info in development: */}
        {process.env.NODE_ENV === 'development' && <p className="text-sm text-gray-500 mt-2">{error.message}</p>}
      </div>
    );
  }

  if (!product) {
    return (
      <div className="container mx-auto p-8 text-center">
        <div className="animate-spin rounded-full h-16 w-16 border-t-2 border-b-2 border-blue-500 mx-auto mb-4"></div>
        <p className="text-gray-700">Loading product details...</p>
      </div>
    );
  }

  const { label, description, price, quantity, code, active, created, imgPath } = product;
    const imageUrl = `http://localhost:8080/api/products/images/get/${imgPath}`;


  return (
    <div className="container mx-auto py-12 px-4">
      <div className="bg-white rounded-lg shadow-md overflow-hidden md:flex">
        <div className="md:w-1/2">
          {imgPath && (
            <img src={imageUrl} alt={label} className="w-full h-full object-cover" />
          )}
        </div>
        <div className="md:w-1/2 p-6 flex flex-col justify-between">
          <div>
            <h1 className="text-3xl font-bold text-gray-800 mb-4">{label}</h1>
            <p className="text-gray-700 leading-relaxed mb-6">{description}</p>
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4 mb-6">
              <div><span className="font-medium">Price:</span> ${price}</div>
              <div><span className="font-medium">Quantity:</span> {quantity}</div>
              {/* <div><span className="font-medium">Code:</span> {code}</div> */}
              {/* <div><span className="font-medium">Status:</span> {active ? 'Active' : 'Inactive'}</div> */}
              {/* <div><span className="font-medium">Created On:</span> {new Date(created).toLocaleDateString()}</div> */}
            </div>
          </div>
            
          <button onClick={handleOrder} className="w-full bg-lime-500 hover:bg-lime-700 text-white font-bold py-3 px-6 rounded transition duration-300">
            Order this Product
          </button>
        </div>
      </div>
    </div>
  );
};

export default ProductDetails;