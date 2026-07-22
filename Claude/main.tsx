import React from 'react'
import ReactDOM from 'react-dom/client'
import StockScreener from './Claude_genrated_stock_screener.tsx'

// Polyfill window.storage
const storagePolyfill = {
  list: async (prefix: string) => {
    const keys = [];
    for (let i = 0; i < localStorage.length; i++) {
      const key = localStorage.key(i);
      if (key && key.startsWith(prefix)) {
        keys.push(key);
      }
    }
    return { keys };
  },
  get: async (key: string) => {
    const value = localStorage.getItem(key);
    return value ? { value } : null;
  },
  set: async (key: string, value: string) => {
    localStorage.setItem(key, value);
  },
  delete: async (key: string) => {
    localStorage.removeItem(key);
  }
};

// @ts-ignore
window.storage = storagePolyfill;

ReactDOM.createRoot(document.getElementById('root')!).render(
  <React.StrictMode>
    <StockScreener />
  </React.StrictMode>,
)