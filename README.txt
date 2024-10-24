ASSUMPTIONS FOR BUILDER TO WORK
    - players ages in years and months respectively have been entered at the end of the .csv with years first then months (columns 10 and 11)
    - friend requests contain full names and are spelled (at least mostly) correctly
    - user knows how many teams they want in the league

THINGS TO KEEP IN MIND
    - friend requests are only considered "reciprocated" if both players have each other as the first valid player name in their requests
        (a requests b, b requests c and a - the request is not reciprocated)
        - three person groups will not be honored regardless of relation
    - non-full name friend requests can pull the wrong player (especially if only the first name is provided)
        (the reciprocation checker checks for the friend's name containing the request or the request containing a valid player's name
        in order to account for nick names being used.
        should usually pull correctly due to the request having to be reciprocated anyway)

Notice: update friend request box note to-
    Request to be on the same team as a single friend. Both players must request each other using full first and last names.
    Please only include one friend. NO GUARANTEES.
