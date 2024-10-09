class Insurance {
  final int? id;
  final String name;
  final String country;
  final List<String> hospitalsCovered;
  final double annualPrice;

  Insurance({
    this.id,
    required this.name,
    required this.country,
    required this.hospitalsCovered,
    required this.annualPrice,
  });

  factory Insurance.fromJson(Map<String, dynamic> json) {
    return Insurance(
      id: json['id'],
      name: json['name'],
      country: json['country'],
      hospitalsCovered: List<String>.from(json['hospitalsCovered']),
      annualPrice: json['annualPrice'].toDouble(),
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'name': name,
      'country': country,
      'hospitalsCovered': hospitalsCovered,
      'annualPrice': annualPrice,
    };
  }
}