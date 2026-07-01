package model;

import java.math.BigDecimal;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "bill_items")
public class BillItem {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bill_id")
    private Bill bill;

    @Column(name = "description")
    private String description;

    @Column(name = "hsn_sac")
    private String hsnSac;

    @Column(name = "qty")
    private BigDecimal qty;

    @Column(name = "rate")
    private BigDecimal rate;

    @Column(name = "amount")
    private BigDecimal amount;

    
    public BillItem() {
		// TODO Auto-generated constructor stub
	}

	public BillItem(Integer id, Bill bill, String description, String hsnSac, BigDecimal qty, BigDecimal rate,
			BigDecimal amount) {
		super();
		this.id = id;
		this.bill = bill;
		this.description = description;
		this.hsnSac = hsnSac;
		this.qty = qty;
		this.rate = rate;
		this.amount = amount;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHsnSac() {
		return hsnSac;
	}

	public void setHsnSac(String hsnSac) {
		this.hsnSac = hsnSac;
	}

	public BigDecimal getQty() {
		return qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}

	public BigDecimal getRate() {
		return rate;
	}

	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "BillItem [id=" + id + ", bill=" + bill + ", description=" + description + ", hsnSac=" + hsnSac
				+ ", qty=" + qty + ", rate=" + rate + ", amount=" + amount + "]";
	}
    
    
}