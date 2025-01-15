import React from 'react';
import { Link } from 'react-router-dom';

const OrderSuccess = () => {
  return (
    <div className="min-h-[calc(100vh-160px)] flex flex-col items-center justify-center">
      <div className="bg-white p-8 rounded-lg shadow-md text-center max-w-md">
        <svg
          className="w-16 h-16 mx-auto mb-4 text-lime-500"
          fill="none"
          stroke="currentColor"
          viewBox="0 0 24 24"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path
            strokeLinecap="round"
            strokeLinejoin="round"
            strokeWidth="2"
            d="M5 13l4 4L19 7"
          ></path>
        </svg>
        <h2 className="text-2xl font-bold text-gray-800 mb-2">Order Successful!</h2>
        <p className="text-gray-600 mb-4">
          Thank you for your order. A confirmation email has been sent to your inbox.
        </p>
        <Link to="/products" className="block bg-lime-600 hover:bg-lime-700 text-white font-medium py-2 px-4 rounded-md transition duration-300">
          Continue Shopping
        </Link>
          <Link to="/" className="mt-4 block bg-gray-300 hover:bg-gray-400 text-gray-700 font-medium py-2 px-4 rounded-md transition duration-300">
              Go to Homepage
          </Link>
      </div>
    </div>
  );
};

export default OrderSuccess;