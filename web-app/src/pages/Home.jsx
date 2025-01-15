import React from 'react';
import { Link } from 'react-router-dom';

const Home = () => {
  return (
    <div className="relative min-h-[calc(100vh-160px)] bg-gradient-to-br from-lime-50 to-blue-50 overflow-hidden">
      <div className="absolute inset-0 bg-[url('https://www.passion-entrepreneur.com/wp-content/uploads/2018/03/ecommerce.jpg')] opacity-20 bg-repeat z-0"></div>

      <div className="relative z-10 container mx-auto px-4 py-24 md:py-32 lg:py-40 text-center"> 
        <h1 className="font-display text-4xl md:text-5xl lg:text-6xl xl:text-7xl font-extrabold text-lime-900 tracking-tight leading-tight mb-8 md:mb-10">
          Unleash Your Shopping Spirit
        </h1>
        <p className="text-lg md:text-xl lg:text-2xl text-gray-700 max-w-4xl mx-auto mb-12 md:mb-16 leading-relaxed"> 
          Discover a world of amazing products at Mega Bazaar. From everyday essentials to unique finds, we've got something for everyone. Explore our curated collections and find your next favorite thing.
        </p>
        <div className="flex flex-col md:flex-row justify-center space-y-4 md:space-y-0 md:space-x-6"> 
          <Link to="/products" className="inline-flex items-center justify-center px-8 py-3 border border-transparent text-base font-medium rounded-md text-white bg-lime-600 hover:bg-lime-700 w-full md:w-auto"> {/* Added w-full */}
            Explore Products
          </Link>
          <Link to="/about" className="inline-flex items-center justify-center px-8 py-3 border border-lime-300 text-base font-medium rounded-md text-lime-700 bg-white hover:bg-lime-50 w-full md:w-auto"> {/* Added w-full */}
            Learn More
          </Link>
        </div>
      </div>

      <div className="absolute bottom-0 left-0 w-full h-24 md:h-32 lg:h-40 bg-lime-100/50 backdrop-blur-sm"> 
        <div className="absolute bottom-0 left-0 w-full h-full bg-[url('/path/to/wave-shape.svg')] bg-repeat-x bg-bottom -translate-y-1/4 bg-contain"></div> 
      </div>
    </div>
  );
};

export default Home;