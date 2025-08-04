import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class NewsService {
    
    public static List<NewsArticle> getSampleNews(String category) {
        List<NewsArticle> articles = new ArrayList<>();
        
        switch (category.toLowerCase()) {
            case "technology":
                articles.add(new NewsArticle(
                    "AI Breakthrough: New Language Model Achieves Human-Level Performance",
                    "Researchers at leading tech companies have developed a revolutionary AI language model that demonstrates human-level performance across multiple cognitive tasks. The model shows remarkable improvements in reasoning, creativity, and problem-solving capabilities. This breakthrough could transform how we interact with AI systems in education, healthcare, and business applications. The research team reports that the model can understand context better than previous iterations and provides more accurate responses to complex queries.",
                    "https://example.com/ai-breakthrough",
                    LocalDateTime.now().minusHours(2),
                    "Technology"
                ));
                
                articles.add(new NewsArticle(
                    "Quantum Computing Reaches Commercial Viability",
                    "A major technology corporation announces the first commercially viable quantum computer for enterprise use. The system can solve complex optimization problems 1000 times faster than traditional computers. Industries including finance, logistics, and drug discovery are expected to benefit significantly from this advancement. The quantum computer uses advanced error correction techniques to maintain stability and reliability for practical applications.",
                    "https://example.com/quantum-commercial",
                    LocalDateTime.now().minusHours(4),
                    "Technology"
                ));
                
                articles.add(new NewsArticle(
                    "Electric Vehicle Sales Surge 300% Globally",
                    "Global electric vehicle sales have increased by 300% compared to last year, driven by improved battery technology and expanding charging infrastructure. Major automakers are investing billions in EV production facilities. Government incentives and environmental concerns are accelerating consumer adoption. The shift towards electric mobility is creating new job opportunities in manufacturing and technology sectors.",
                    "https://example.com/ev-surge",
                    LocalDateTime.now().minusHours(6),
                    "Technology"
                ));
                break;
                
            case "world":
                articles.add(new NewsArticle(
                    "Global Climate Summit Reaches Historic Agreement",
                    "World leaders from 195 countries have reached a historic climate agreement at the Global Climate Summit. The accord includes binding commitments to reduce carbon emissions by 50% within the next decade. Developed nations pledge $500 billion in climate financing for developing countries. The agreement also establishes new frameworks for renewable energy cooperation and technology transfer between nations.",
                    "https://example.com/climate-agreement",
                    LocalDateTime.now().minusHours(1),
                    "World"
                ));
                
                articles.add(new NewsArticle(
                    "International Space Station Welcomes New Research Module",
                    "The International Space Station has successfully integrated a new research module designed for advanced scientific experiments. The module will focus on medical research, materials science, and Earth observation studies. International cooperation continues to strengthen as space agencies from multiple countries contribute to ongoing research. The facility now supports expanded research capabilities for studying the effects of microgravity on various biological and physical processes.",
                    "https://example.com/iss-module",
                    LocalDateTime.now().minusHours(3),
                    "World"
                ));
                break;
                
            case "business":
                articles.add(new NewsArticle(
                    "Global Markets Rally on Strong Economic Data",
                    "Stock markets worldwide are experiencing significant gains following the release of positive economic indicators. Unemployment rates have dropped to historic lows while consumer spending shows robust growth. Technology and renewable energy sectors are leading the rally with impressive quarterly earnings. Analysts predict continued growth as inflation rates stabilize and interest rates remain favorable for business investment.",
                    "https://example.com/market-rally",
                    LocalDateTime.now().minusHours(1),
                    "Business"
                ));
                
                articles.add(new NewsArticle(
                    "Startup Funding Reaches Record High in Green Technology",
                    "Venture capital investment in green technology startups has reached an all-time high of $150 billion this quarter. Clean energy, sustainable agriculture, and carbon capture technologies are attracting the most significant investments. This funding surge reflects growing investor confidence in environmental solutions and regulatory support for sustainable business practices. Many startups are focusing on scalable solutions that can address climate change while generating substantial returns.",
                    "https://example.com/green-funding",
                    LocalDateTime.now().minusHours(5),
                    "Business"
                ));
                break;
                
            case "sports":
                articles.add(new NewsArticle(
                    "Olympic Games Set New Viewership Records",
                    "The latest Olympic Games have achieved unprecedented global viewership numbers, with over 4 billion people tuning in across various platforms. Digital streaming platforms saw a 400% increase in engagement compared to previous Olympics. New sports categories and enhanced broadcast technology contributed to the increased interest. Athletes from emerging nations achieved breakthrough performances, inspiring new generations of sports enthusiasts worldwide.",
                    "https://example.com/olympics-viewership",
                    LocalDateTime.now().minusHours(2),
                    "Sports"
                ));
                break;
                
            case "health":
                articles.add(new NewsArticle(
                    "Revolutionary Gene Therapy Shows Promise for Rare Diseases",
                    "Clinical trials for a new gene therapy treatment have shown remarkable success in treating previously incurable rare genetic diseases. The therapy has demonstrated a 95% success rate in early trials with minimal side effects. Patients who received the treatment showed significant improvement in symptoms and quality of life. This breakthrough could pave the way for treating thousands of rare genetic conditions affecting millions of people worldwide.",
                    "https://example.com/gene-therapy",
                    LocalDateTime.now().minusHours(3),
                    "Health"
                ));
                break;
                
            default:
                articles.add(new NewsArticle(
                    "Breaking: Local Community Launches Innovation Hub",
                    "A new community-driven innovation hub has opened, providing resources and support for local entrepreneurs and students. The facility features modern co-working spaces, advanced technology labs, and mentorship programs. Local government and business leaders collaborate to foster innovation and economic growth. The hub aims to create opportunities for skill development and startup incubation in the region.",
                    "https://example.com/innovation-hub",
                    LocalDateTime.now().minusHours(1),
                    "Local"
                ));
        }
        
        return articles;
    }
}
