package org.d3if3121.tellink.data.model

class Tag {
    val tags: List<String> = listOf(
        // Teknologi & Pemrograman
        "Android", "Kotlin", "Java", "Flutter", "React Native", "Swift", "Dart", "JavaScript", "Python", "C++", "C#", "Ruby",
        "JavaScript", "TypeScript", "PHP", "HTML", "CSS", "Node.js", "Express", "GraphQL", "Spring Boot", "ASP.NET", "Angular",
        "Vue.js", "Laravel", "Bootstrap", "TailwindCSS", "Svelte", "Rust", "Go", "ReactJS", "Next.js", "Redux", "SQL", "NoSQL",
        "MongoDB", "Firebase", "MySQL", "PostgreSQL", "SQLite", "GraphQL", "Docker", "Kubernetes", "AWS", "Google Cloud", "Azure",

        // Data Science & Machine Learning
        "Data Science", "Machine Learning", "Artificial Intelligence", "Deep Learning", "Natural Language Processing", "Computer Vision",
        "TensorFlow", "PyTorch", "Keras", "OpenCV", "Pandas", "Numpy", "Scikit-learn", "XGBoost", "Kaggle", "Neural Networks", "Reinforcement Learning",
        "Data Analysis", "Big Data", "Hadoop", "Spark", "R", "Julia", "SQL", "Data Visualization", "Power BI", "Tableau",

        // Internet of Things (IoT)
        "IoT", "Arduino", "Raspberry Pi", "ESP32", "Bluetooth", "MQTT", "Sensors", "Actuators", "Smart Home", "Wearables",
        "Edge Computing", "Home Automation", "Wireless Communication", "RFID", "NFC", "5G",

        // Game Development
        "Game Development", "Unity", "Unreal Engine", "Cocos2d", "Blender", "3D Modeling", "Game Design", "VR", "AR",
        "Game Mechanics", "AI in Games", "Game Programming", "Multiplayer", "Simulation",

        // Cybersecurity
        "Cybersecurity", "Network Security", "Penetration Testing", "Ethical Hacking", "Cryptography", "Malware Analysis",
        "Security Operations", "SIEM", "Firewalls", "Vulnerability Assessment", "Intrusion Detection",

        // Business & Entrepreneurship
        "Entrepreneurship", "Business Development", "Marketing", "Digital Marketing", "Social Media Marketing", "Content Creation",
        "Branding", "E-commerce", "Fintech", "Blockchain", "Cryptocurrency", "Startups", "Business Strategy", "Business Analysis",
        "Investment", "Fundraising", "Pitching", "Customer Acquisition", "Growth Hacking", "Product Management", "Agile",
        "Scrum", "Lean Startup", "B2B", "B2C", "Sales",

        // Design & User Experience
        "UI/UX Design", "Web Design", "Mobile Design", "Prototyping", "Wireframing", "Figma", "Sketch", "Adobe XD", "Photoshop",
        "Illustrator", "Design Systems", "User Research", "User Testing", "Interaction Design", "Information Architecture", "Motion Design",
        "Design Thinking", "Human-Centered Design",

        // Cloud Computing & DevOps
        "Cloud Computing", "DevOps", "Continuous Integration", "Continuous Deployment", "Automation", "Infrastructure as Code",
        "CI/CD", "AWS", "Google Cloud", "Azure", "Docker", "Kubernetes", "Terraform", "Serverless Architecture", "Virtualization", "Git",

        // Blockchain & Cryptocurrency
        "Blockchain", "Cryptocurrency", "Smart Contracts", "Ethereum", "Bitcoin", "DeFi", "NFT", "Solidity", "Blockchain Development",
        "Tokenomics", "Cryptography", "Distributed Ledger", "Blockchain Security", "Proof of Work", "Proof of Stake", "Web3", "Decentralized Apps",

        // Social Impact & Sustainability
        "Sustainability", "Climate Change", "Social Impact", "NGO", "Corporate Social Responsibility", "Recycling", "Sustainable Development",
        "Environmental Studies", "Green Technology", "Renewable Energy", "Eco-Friendly", "Social Good", "Climate Tech",

        // Multimedia & Arts
        "Photography", "Videography", "Film Production", "Animation", "2D Animation", "3D Animation", "Graphic Design", "Motion Graphics",
        "Audio Engineering", "Music Production", "Sound Design", "Music Theory", "Art History", "Fine Arts", "Street Art", "Graphic Novels",
        "Storytelling", "Scriptwriting",

        // Virtual & Augmented Reality
        "Virtual Reality", "Augmented Reality", "Mixed Reality", "VR Games", "AR Applications", "HoloLens", "VR Development",
        "3D Modeling for AR", "360 Video", "Interactive Experiences",

        // Software Testing & Quality Assurance
        "Software Testing", "Quality Assurance", "Automation Testing", "Unit Testing", "Integration Testing", "UI Testing", "Test Driven Development",
        "Selenium", "Jest", "Mocha", "Cypress", "JUnit", "Test Automation", "Performance Testing",

        // Robotics
        "Robotics", "Robotic Process Automation", "Robot Design", "Autonomous Robots", "AI in Robotics", "Industrial Robotics", "Drone Technology",
        "Robot Programming", "Robotic Simulation",

        // Other Tags
        "Research", "Innovation", "Collaboration", "Prototyping", "Agile Methodology", "Hackathons", "Open Source", "Crowdsourcing", "Community Engagement"
    )


    fun getFilteredTags(query: String): List<String> {
        return tags.filter { it.contains(query, ignoreCase = true) }
    }
}
