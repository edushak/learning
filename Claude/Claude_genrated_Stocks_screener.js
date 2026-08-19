import React, { useState, useEffect } from 'react';
import { TrendingUp, DollarSign, Calendar, RefreshCw, BarChart3, ChevronDown, ChevronUp } from 'lucide-react';

export default function StockScreener() {
  const [loading, setLoading] = useState(false);
  const [recommendation, setRecommendation] = useState(null);
  const [history, setHistory] = useState([]);
  const [error, setError] = useState(null);
  const [expandedStock, setExpandedStock] = useState(null);

  useEffect(() => {
    loadHistory();
    checkDailyRecommendation();
  }, []);

  const loadHistory = async () => {
    try {
      const result = await window.storage.list('stock:');
      if (result && result.keys) {
        const historyData = [];
        for (const key of result.keys) {
          const data = await window.storage.get(key);
          if (data) {
            historyData.push(JSON.parse(data.value));
          }
        }
        historyData.sort((a, b) => new Date(b.date) - new Date(a.date));
        setHistory(historyData);
      }
    } catch (err) {
      console.log('No history found yet');
    }
  };

  const checkDailyRecommendation = async () => {
    const today = new Date().toISOString().split('T')[0];
    try {
      const result = await window.storage.get(`stock:${today}`);
      if (result) {
        const data = JSON.parse(result.value);
        setRecommendation(data);
      }
    } catch (err) {
      console.log('No recommendation for today yet');
    }
  };

  const generateRecommendation = async () => {
    setLoading(true);
    setError(null);

    try {
      const response = await fetch("https://api.anthropic.com/v1/messages", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          model: "claude-sonnet-4-20250514",
          max_tokens: 4000,
          tools: [{
            type: "web_search_20250305",
            name: "web_search"
          }],
          messages: [{
            role: "user",
            content: `Search for stocks that meet ALL of these specific criteria:

REQUIRED CRITERIA (ALL must be met):
1. P/E ratio: 15 or less
2. Price/Book ratio: 2 or less
3. EPS growth (5-year historical): 15% or above
4. Revenue (TTM): $400M or above
5. Stock price: Above $5
6. Current ratio (Current Assets/Current Liabilities): 2 or above
7. Dividend growth rate (5-year average): Positive (must be paying and growing dividends)

Search for stocks that meet ALL these criteria. Find 3-5 stocks if possible, but quality over quantity - only include stocks that truly meet all requirements.

For each stock, provide:
- Ticker symbol and company name
- Current price
- P/E ratio
- Price/Book ratio
- EPS growth rate (5-year)
- Revenue (TTM)
- Current ratio
- Dividend growth rate (5-year average)
- Brief reasoning why it meets the criteria and has growth potential

Return ONLY a JSON object with this structure (no preamble, no markdown):
{
  "stocks": [
    {
      "ticker": "XYZ",
      "name": "Company Name Inc.",
      "price": 45.50,
      "pe": 12.5,
      "priceToBook": 1.8,
      "epsGrowth5Y": "18%",
      "revenueTTM": "$850M",
      "currentRatio": 2.3,
      "dividendGrowth5Y": "8%",
      "reasoning": "Meets all value criteria with strong balance sheet..."
    }
  ],
  "date": "${new Date().toISOString().split('T')[0]}",
  "marketContext": "Brief overview of current market conditions and how many stocks met the strict criteria"
}`
          }]
        })
      });

      const data = await response.json();

      let textContent = data.content
        .filter(item => item.type === "text")
        .map(item => item.text)
        .join("\n");

      textContent = textContent.replace(/```json|```/g, "").trim();
      const parsed = JSON.parse(textContent);

      await window.storage.set(`stock:${parsed.date}`, JSON.stringify(parsed));

      setRecommendation(parsed);
      await loadHistory();
    } catch (err) {
      setError("Failed to generate recommendation. Please try again.");
      console.error(err);
    } finally {
      setLoading(false);
    }
  };

  const toggleExpand = (ticker) => {
    setExpandedStock(expandedStock === ticker ? null : ticker);
  };

  return (
    <div className="min-h-screen bg-gradient-to-br from-slate-900 via-slate-800 to-slate-900 text-white p-6">
      <div className="max-w-6xl mx-auto">
        <header className="mb-8">
          <div className="flex items-center justify-between">
            <div>
              <h1 className="text-4xl font-bold mb-2 flex items-center gap-3">
                <TrendingUp className="text-emerald-400" size={40} />
                AI Stock Screener
              </h1>
              <p className="text-slate-400">Daily bargain stocks positioned for growth</p>
            </div>
                          <button
              onClick={generateRecommendation}
              disabled={loading}
              className="bg-emerald-600 hover:bg-emerald-700 disabled:bg-slate-600 px-6 py-3 rounded-lg font-semibold flex items-center gap-2 transition-colors"
            >
              <RefreshCw className={loading ? "animate-spin" : ""} size={20} />
              {loading ? "Analyzing..." : "Generate New Analysis"}
            </button>
          </div>
        </header>

        {error && (
          <div className="bg-red-900/50 border border-red-700 rounded-lg p-4 mb-6">
            <p className="text-red-200">{error}</p>
          </div>
        )}

        {recommendation && (
          <div className="mb-8">
            <div className="bg-slate-800/50 backdrop-blur rounded-xl p-6 border border-slate-700 mb-4">
              <div className="flex items-center gap-2 mb-4">
                <Calendar className="text-emerald-400" size={20} />
                <h2 className="text-2xl font-bold">Today's Recommendations</h2>
                <span className="text-slate-400 ml-2">{recommendation.date}</span>
              </div>

              {recommendation.marketContext && (
                <div className="bg-slate-700/30 rounded-lg p-4 mb-6">
                  <p className="text-slate-300 leading-relaxed">{recommendation.marketContext}</p>
                </div>
              )}

              <div className="grid gap-4">
                {recommendation.stocks.map((stock) => (
                  <div key={stock.ticker} className="bg-slate-700/40 rounded-lg border border-slate-600 overflow-hidden">
                    <div
                      className="p-5 cursor-pointer hover:bg-slate-700/60 transition-colors"
                      onClick={() => toggleExpand(stock.ticker)}
                    >
                      <div className="flex items-start justify-between">
                        <div className="flex-1">
                          <div className="flex items-center gap-3 mb-2">
                            <span className="bg-emerald-600 text-white px-3 py-1 rounded-md font-mono font-bold">
                              {stock.ticker}
                            </span>
                            <h3 className="text-xl font-semibold">{stock.name}</h3>
                          </div>

                          <div className="grid grid-cols-2 md:grid-cols-4 gap-4 mt-4">
                            <div>
                              <p className="text-slate-400 text-sm">Price</p>
                              <p className="text-lg font-semibold flex items-center gap-1">
                                <DollarSign size={16} />
                                {stock.price}
                              </p>
                            </div>
                            <div>
                              <p className="text-slate-400 text-sm">P/E Ratio</p>
                              <p className="text-lg font-semibold text-emerald-400">{stock.pe}</p>
                            </div>
                            <div>
                              <p className="text-slate-400 text-sm">P/B Ratio</p>
                              <p className="text-lg font-semibold text-emerald-400">{stock.priceToBook}</p>
                            </div>
                            <div>
                              <p className="text-slate-400 text-sm">EPS Growth (5Y)</p>
                              <p className="text-lg font-semibold text-emerald-400">{stock.epsGrowth5Y}</p>
                            </div>
                          </div>
                        </div>

                        <button className="ml-4 text-slate-400 hover:text-white transition-colors">
                          {expandedStock === stock.ticker ? <ChevronUp size={24} /> : <ChevronDown size={24} />}
                        </button>
                      </div>
                    </div>

                    {expandedStock === stock.ticker && (
                      <div className="px-5 pb-5 pt-2 border-t border-slate-600">
                        <div className="grid grid-cols-2 md:grid-cols-3 gap-4 mb-4">
                          <div>
                            <p className="text-slate-400 text-sm mb-1">Revenue (TTM)</p>
                            <p className="text-lg font-semibold">{stock.revenueTTM}</p>
                          </div>
                          <div>
                            <p className="text-slate-400 text-sm mb-1">Current Ratio</p>
                            <p className="text-lg font-semibold text-emerald-400">{stock.currentRatio}</p>
                          </div>
                          <div>
                            <p className="text-slate-400 text-sm mb-1">Dividend Growth (5Y)</p>
                            <p className="text-lg font-semibold text-emerald-400">{stock.dividendGrowth5Y}</p>
                          </div>
                        </div>
                        <div>
                          <p className="text-slate-400 text-sm mb-2">Investment Thesis</p>
                          <p className="text-slate-200 leading-relaxed">{stock.reasoning}</p>
                        </div>
                      </div>
                    )}
                  </div>
                ))}
              </div>
            </div>
          </div>
        )}

        {history.length > 0 && (
          <div className="bg-slate-800/50 backdrop-blur rounded-xl p-6 border border-slate-700">
            <div className="flex items-center gap-2 mb-4">
              <BarChart3 className="text-blue-400" size={20} />
              <h2 className="text-2xl font-bold">Historical Recommendations</h2>
            </div>

            <div className="space-y-3">
              {history.slice(0, 10).map((rec) => (
                <div key={rec.date} className="bg-slate-700/30 rounded-lg p-4 border border-slate-600">
                  <p className="text-slate-300 font-semibold mb-2">{rec.date}</p>
                  <div className="flex flex-wrap gap-2">
                    {rec.stocks.map((stock) => (
                      <span key={stock.ticker} className="bg-slate-600 px-3 py-1 rounded-md text-sm font-mono">
                        {stock.ticker}
                      </span>
                    ))}
                  </div>
                </div>
              ))}
            </div>
          </div>
        )}

        {!recommendation && !loading && history.length === 0 && (
          <div className="text-center py-16">
            <TrendingUp className="mx-auto mb-4 text-slate-600" size={64} />
            <p className="text-slate-400 text-lg">Click "Generate New Analysis" to start screening for opportunities</p>
          </div>
        )}
      </div>
    </div>
  );
}