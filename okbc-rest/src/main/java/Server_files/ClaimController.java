package Server_files;

import WikiDataItem.Claims;

import java.util.List;

import static spark.Spark.*;

public class ClaimController {
    public ClaimController(final ClaimService claimService) {

        get("/claims/:id", (req, res) -> {
            String id = req.params(":id");
            List<Claims> claim = ClaimService.getClaimbyid(id);
            /*if(claim!=null)
                return claim;
            res.status(400);
            return new ResponseError("No user with id '%s' found", id);
            */
            return claim;
        });

        get("/claims", (req, res) -> ClaimService.getAllClaims());
/*
        post("/claims/post/:id", (req,res)->ClaimService.createClaims(
                req.params(":id"),
                req.queryParams("type")
        ));

        put("/users/:id", (req,res)->claimService.updateClaim(
                req.params(":id"),
                req.queryParams("name"),
                req.queryParams("email")
        ));
    */
    }

}
