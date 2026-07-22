/// <reference types="vite/client" />

interface Window {
  storage: {
    list: (prefix: string) => Promise<{ keys: string[] }>;
    get: (key: string) => Promise<{ value: string } | null>;
    set: (key: string, value: string) => Promise<void>;
    delete: (key: string) => Promise<void>;
  }
}