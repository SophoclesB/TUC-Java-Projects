package backend.subscription;

public class SubscriptionPlan {
	private String planName;
	private double planPrice;
	private int planMaxScreens;

	public SubscriptionPlan(String name, double price, int maxScreens) {
		if (name == null || name.trim().isEmpty())
			throw new IllegalArgumentException("ERROR: Plan name cannot be empty");
		if (price <= 0)
			throw new IllegalArgumentException("ERROR: Price cannot be negative or zero");
		if (maxScreens <= 0)
			throw new IllegalArgumentException("ERROR: Max screens cannot be negative or zero");
		this.planName = name;
		this.planPrice = price;
		this.planMaxScreens = maxScreens;
	}

	protected String getPlanName() {
		return planName;
	}

	protected double getPlanPrice() {
		return planPrice;
	}

	protected int getPlanMaxScreens() {
		return planMaxScreens;
	}
}
