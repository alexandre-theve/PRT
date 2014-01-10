SELECT e.* from (SELECT eht.*, count(eht.Tags_id) as nb FROM prt.user_has_evenement uhe 
	join evenement_has_tags eht on uhe.Evenement_id = eht.Evenement_id
where user_id=1 
group by eht.Tags_id 
order by nb desc 
limit 3) bestTags
	JOIN evenement_has_tags eht on eht.Tags_id = bestTags.Tags_id
		JOIN evenement e ON eht.Evenement_id = e.id
GROUP BY e.id;